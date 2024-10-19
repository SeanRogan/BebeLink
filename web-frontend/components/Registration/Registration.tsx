'use client';
import React, {useState} from 'react';
import {Alert, AppShell, Box, Button, Group, PasswordInput, Text, TextInput} from '@mantine/core';
import {useForm} from '@mantine/form';
import {useAuthContext} from "@/app/context/AuthContext";
import DefaultNavBar from "@/components/DynamicNavbar/DefaultNavBar";
import {NavigationProps} from "@/app/util/types";
import { Footer } from '../Footer/Footer';

export default function Registration({onNavigate}: NavigationProps) {
    const [errorMessage, setErrorMessage] = useState('');
    const auth = useAuthContext();
    
    const form = useForm({
        name: 'registration-form',
        initialValues: {
            username: '',
            email: '',
            password: '',
            passwordRepeat: '',
            role: 'ROLE_USER_PAID'
        },

        validate: {
            email: (value) => (/^\S+@\S+$/.test(value) ? null : 'Invalid email'),
            password: (value) => (value.length >= 8 ? null : 'Please choose a password of 8 characters in length or more.'),
            passwordRepeat: (value, values) => (value === values.password ? null : 'Passswords do not match.'),
        },
    });

    const handleSubmit = async (values: typeof form.values) => {
        try {
            const res = await auth.registerUser(values);
            
            if (res?.success) {
                form.reset();
                onNavigate('authenticatedHome');
            } else {
                setErrorMessage('Registration was unsuccessful. Please try again.');
            }
        } catch (error) {
            console.error('Error in registration:', error);
            setErrorMessage('An error occurred during registration. Please try again.');
        }
    };

    return (
        <AppShell header={{height: 70}} padding="md">
            <AppShell.Header>
                <DefaultNavBar onNavigate={onNavigate}/>
            </AppShell.Header>

            <AppShell.Main>
                <Box maw={340} mx="auto">
                    <Text size="xl">Sign up for an account</Text>
                    
                    <form onSubmit={form.onSubmit(handleSubmit)}>
                        <TextInput
                            withAsterisk
                            label="Choose a username"
                            placeholder="enter username"
                            {...form.getInputProps('username')}
                        />
                        <TextInput
                            withAsterisk
                            label="Enter your email address"
                            placeholder="enter email address"
                            {...form.getInputProps('email')}
                        />
                        <PasswordInput
                            withAsterisk
                            label="Choose a password"
                            placeholder="enter password"
                            {...form.getInputProps('password')}
                        />
                        <PasswordInput
                            withAsterisk
                            label="Confirm your password"
                            placeholder="confirm password"
                            {...form.getInputProps('passwordRepeat')}
                        />
                        <Group justify="center" mt="md">
                            <Button type="submit">
                                Submit
                            </Button>
                            <Button variant="subtle" onClick={() => onNavigate("login")}>
                                Already have an account? Log in here
                            </Button>
                        </Group>
                    </form>

                    {errorMessage && (
                        <Alert title="Registration Failure" color="red" mt="md">
                            {errorMessage}
                        </Alert>
                    )}
                </Box>
            </AppShell.Main>
            <AppShell.Footer>
                <Footer />
            </AppShell.Footer>
        </AppShell>
    );
}

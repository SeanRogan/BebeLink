'use client';
import React, {useState} from 'react';
import {Alert, AppShell, Box, Button, Group, PasswordInput, Text, TextInput} from '@mantine/core';
import {useForm} from '@mantine/form';
import {useAuthContext} from "@/app/context/AuthContext";
import DefaultNavBar from "@/components/DynamicNavbar/DefaultNavBar";
import AuthenticatedNavBar from "@/components/DynamicNavbar/AuthenticatedNavBar";
import {NavigationProps} from "@/app/util/types";
import { Footer } from '../Footer/Footer';

export default function Login({onNavigate}: NavigationProps) {
    const form = useForm({
        initialValues: {
            email: '',
            password: '',
        },
        validate: {
            email: (value) => (/^\S+@\S+$/.test(value) ? null : 'Invalid email'),
            password: (value) => (value.length >= 8 ? null : 'Password must be at least 8 characters'),
        },
    });

    const [errorMessage, setErrorMessage] = useState('');
    const auth = useAuthContext();

    const handleSubmit = async (values: typeof form.values) => {
        const errors = form.validate();
        if (errors.hasErrors) {
            return;
        }

        try {
            const ok = await auth.loginUser(values);
            if (ok) {
                onNavigate('authenticatedHome');
            } else {
                setErrorMessage('The email address and/or password was incorrect. Please try again.');
            }
        } catch (error) {
            console.error('Login failed:', error);
            setErrorMessage('An error occurred during login. Please try again later.');
        }
    };

    return (
        <AppShell header={{height: 70}} padding="md">
            <AppShell.Header>
                {auth.user ? (
                    <AuthenticatedNavBar 
                        onNavigate={onNavigate} 
                        onHomeClick={() => onNavigate('authenticatedHome')}
                    />
                ) : (
                    <DefaultNavBar onNavigate={onNavigate} />
                )}
            </AppShell.Header>

            <AppShell.Main>
                <Box maw={340} mx="auto">
                    <Text size="xl">Log in to your account</Text>
                    <form onSubmit={form.onSubmit(handleSubmit)}>
                        <TextInput
                            withAsterisk
                            label="Email"
                            placeholder="your@email.com"
                            {...form.getInputProps('email')}
                        />
                        <PasswordInput
                            withAsterisk
                            label="Password"
                            placeholder="******************"
                            {...form.getInputProps('password')}
                        />
                        <Group justify="center" mt="md">
                            <Button type="submit">Submit</Button>
                            <Button type="button" onClick={() => onNavigate('register')}>
                                Don't have an account? Sign up here
                            </Button>
                        </Group>
                    </form>
                    {errorMessage && (
                        <Alert title="Login failed" color="red" withCloseButton onClose={() => setErrorMessage('')}>
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

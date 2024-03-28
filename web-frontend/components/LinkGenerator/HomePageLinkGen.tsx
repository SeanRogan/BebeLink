'use client';
import {Button, Card, Group, Space, Stack, TextInput, Title} from '@mantine/core';
import {useForm} from '@mantine/form';

export default function HomePageLinkGen() {
    const form = useForm({
        initialValues: {
            url: '',
        },

        validate: {
            // Validate the url to make sure it starts with http:// or https://
            url: (value) =>
                /^(http:\/\/|https:\/\/).+/.test(value) ? null : 'Please enter a valid URL.(starts with http:// or https://)',
        },
    });

    const handleSubmit = (values) => {
        // Handle the submission of the form
        // You can perform actions such as routing or fetching data based on the URL here
        alert(`URL: ${values.url}`);
    };

    return (
        <Card shadow="xl" padding="lg" radius="md" withBorder style={{maxWidth: 400, margin: 'auto'}}>
            <Stack>
            <Title size="2.2rem">Shorten a long URL</Title>
            <Group justify="flex-start">
                <form onSubmit={form.onSubmit(handleSubmit)}>
                    <TextInput
                        placeholder="Enter your link here"
                        {...form.getInputProps('url')}
                    />

                </form>
                    <Button type="submit">Get My URL!</Button>
            </Group>
            </Stack>
        </Card>
    );
};

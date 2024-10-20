'use client';
import React, { useState } from 'react';
import { Button, TextInput, Text, Loader } from '@mantine/core';
import { useForm } from '@mantine/form';
import axios from 'axios';

interface HomePageLinkGenProps {
    onLinkGenerated: (link: string) => void;
    isLoading: boolean;
    onError: (errorMessage: string) => void;
}

export default function HomePageLinkGen({ onLinkGenerated, isLoading, onError }: HomePageLinkGenProps) {
    const [generatedLink, setGeneratedLink] = useState('');

    const form = useForm({
        initialValues: {
            url: '',
        },
        validate: {
            url: (value) =>
                /^(http:\/\/|https:\/\/).+/.test(value) ? null : 'Please enter a valid URL (starts with http:// or https://)',
        },
    });

    const handleSubmit = async (values) => {
        try {
            const response = await axios.post('http://localhost:8081/generator-service/generate', {
                longUrl: values.url
            });

            const shortUrl = response.data.shortUrl;
            setGeneratedLink(shortUrl);
            onLinkGenerated(shortUrl);
        } catch (error) {
            console.error('Error generating short URL:', error);
            onError(`There was an error. Please contact support.`);
        }
    };

    return (
        <form onSubmit={form.onSubmit(handleSubmit)}>
            <TextInput
                required
                label="Enter your long URL"
                placeholder="https://example.com"
                {...form.getInputProps('url')}
            />
            <Button type="submit" loading={isLoading}>
                Generate Short URL
            </Button>
            {generatedLink && (
                <Text>
                    Your shortened URL: <a href={generatedLink} target="_blank" rel="noopener noreferrer">{generatedLink}</a>
                </Text>
            )}
        </form>
    );
}

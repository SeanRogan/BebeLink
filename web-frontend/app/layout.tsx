import '@mantine/core/styles.css';
import React from 'react';
import {ColorSchemeScript, MantineProvider} from '@mantine/core';
import {theme} from '@/theme';

export default function RootLayout({children}: { children: any }) {
    return (
        <html lang="en">
        <head>
            <ColorSchemeScript/>
            <meta
                name="viewport"
                content="minimum-scale=1, initial-scale=1, width=device-width, user-scalable=no"
            />
            <title>Bebe.Link</title>

        </head>
        <body>
        <MantineProvider theme={theme}>{children}</MantineProvider>
        </body>
        </html>
    );
}

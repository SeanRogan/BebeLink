'use client';

import {createTheme, MantineThemeOverride, rem, MantineColorsTuple} from '@mantine/core';

const themeColorsLight = [
        "#E0EFFF", // Lightest shade
        "#C2DFFF",
        "#A3CFFF",
        "#85BFFF",
        "#66AFFF",
        "#4263EB", // Primary color
        "#3A59D9",
        "#324FC7",
        "#2A45B5",
        "#2239A3"  // Darkest shade
    ];

const secondaryColorsLight = [
        "#F7E8FF", // Lightest shade
        "#EECFFF",
        "#E5B6FF",
        "#DC9DFF",
        "#D384FF",
        "#9967FF", // Secondary color
        "#8A5DE6",
        "#7B53CC",
        "#6C49B3",
        "#5D3F99"  // Darkest shade
    ];

export const theme = createTheme({
        colorScheme: {
            light: {colors: { 
                primary: themeColorsLight as unknown as MantineColorsTuple,
                secondary: secondaryColorsLight as unknown as MantineColorsTuple,
            },
            primaryColor: 'primary',
            primaryShade: 5,
            shadows: {
                md: '0 4px 8px rgba(0, 0, 0, 0.1)',
            },
            components: {
                Button: {
                    styles: (theme) => ({
                        root: {
                            transition: 'all 0.3s ease',
                            '&:hover': {
                                transform: 'translateY(-2px)',
                                boxShadow: theme.shadows.md,
                            },
                        },
                    }),
                },
            },},
            dark: {},
        },
        
    } as MantineThemeOverride);

'use client';

import {createTheme, MantineThemeOverride, rem} from '@mantine/core';

const themeColors = [
        "#fff8e0",
        "#ffefca",
        "#ffdd99",
        "#ffcb62",
        "#ffbb36",
        "#ffb118",
        "#ffac03",
        "#e49600",
        "#ca8400",
        "#b07200"
    ];

export const theme = createTheme(<MantineThemeOverride>{
        colors: { orange: themeColors,},
        headings: {
                sizes: {
                        h1: { fontSize: ""}
                }

        },
});
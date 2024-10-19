import {ActionIcon, Button, Group, rem, Title} from "@mantine/core";
import {IconHome, IconLogin2} from "@tabler/icons-react";
import {ColorSchemeToggle} from "@/components/ColorSchemeToggle/ColorSchemeToggle";
import React from "react";
import {NavigationProps} from "@/app/util/types";

export default function DefaultNavBar({onNavigate}: NavigationProps) {
    const loginIcon = <IconLogin2 style={{width: rem(20), height: rem(20)}}/>;

    return (
        <div style={{
            background: 'linear-gradient(to right, #1a237e, #4a148c)', // Added gradient background
            padding: '18px 16px', // Added padding
        }}>
            <Group style={{
                height: '100%',
                width: '100%',
                justifyContent: 'space-between',
                alignItems: 'center',
            }}>
                <Group>
                    <ActionIcon onClick={() => {
                        onNavigate('home')
                    }} variant="gradient" size="xl"
                                gradient={{from: "blue", to: "cyan", deg: 75}}><IconHome/>BEBE</ActionIcon>
                </Group>
                <Group>
                    <Button leftSection={loginIcon} onClick={() => {
                        onNavigate('login')
                    }}>Sign In</Button>
                    <ColorSchemeToggle/>
                </Group>
            </Group>
        </div>
    );
}

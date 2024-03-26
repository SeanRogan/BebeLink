import {ActionIcon, Button, Divider, Group, Menu, rem, Title} from "@mantine/core";
import {
    IconHome,
    IconInfoCircle,
    IconLogout2,
    IconMountain,
    IconTemperature,
    IconUserCircle
} from "@tabler/icons-react";
import {ColorSchemeToggle} from "@/components/ColorSchemeToggle/ColorSchemeToggle";
import React from "react";
import {useAuthContext} from "@/app/context/AuthContext";

export default function AuthenticatedNavBar({onNavigate, onHomeClick}) {
    const auth = useAuthContext();
    const homeIcon = <IconUserCircle style={{width: rem(20), height: rem(20)}}/>
    return (
        <>
            <Group style={{
                height: '100%',
                width: '100%',
                justifyContent: 'space-between',
                alignItems: 'center',
                padding: '0 16px'
            }}>
                <Group>
                    <ActionIcon onClick={async () => {
                        onNavigate('authenticatedHome');
                        await onHomeClick();
                    }} variant="gradient" size="xl"
                                gradient={{from: "blue", to: "cyan", deg: 75}}>
                        <IconHome/>
                    </ActionIcon>
                    <Title order={1}>
                        Bebe.Link
                    </Title>
                </Group>
                <Group>
                    <Menu shadow="lg" width="200px">
                        <Menu.Target>
                            <Button leftSection={homeIcon}>Account</Button>
                        </Menu.Target>
                        <Menu.Dropdown>
                            <Menu.Item onClick={() => {
                                onNavigate('favorites')
                            }} leftSection={<IconMountain style={{width: rem(20), height: rem(20)}}/>}>
                                My Links
                            </Menu.Item>
                            <Menu.Item onClick={() => {
                                onNavigate('profile')
                            }} leftSection={<IconTemperature style={{width: rem(20), height: rem(20)}}/>}>
                                Settings
                            </Menu.Item>
                            <Divider/>
                            <Menu.Item onClick={() => {
                                auth.logoutUser();
                                onNavigate('home')
                            }} leftSection={<IconLogout2 style={{width: rem(20), height: rem(20)}}/>}>
                                Log out
                            </Menu.Item>
                        </Menu.Dropdown>
                    </Menu>
                    <ColorSchemeToggle/>
                </Group>
            </Group>
        </>
    );
}
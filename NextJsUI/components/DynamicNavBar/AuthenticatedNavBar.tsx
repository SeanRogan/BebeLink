import {ActionIcon, Button, Divider, Group, Menu, rem, Title} from "@mantine/core";
import {
    IconHome,
    IconInfoCircle,
    IconLogout2,
    IconMountain,
    IconTemperature,
    IconUserCircle
} from "@tabler/icons-react";
import React from "react";
import {ColorSchemeToggle} from "../ColorSchemeToggle/ColorSchemeToggle";
import {useAppContext} from "../../app/context/ApplicationContext";

export default function AuthenticatedNavBar({onNavigate, onHomeClick}) {
    const auth = useAppContext();
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
                        Peak Conditions
                    </Title>
                </Group>
                <Group>
                    <Menu shadow="lg" width="200px">
                        <Menu.Target>
                            <Button leftSection={homeIcon}>Account</Button>
                        </Menu.Target>
                        <Menu.Dropdown>
                            <Menu.Item onClick={() => {
                                onNavigate('My Links')
                            }} leftSection={<IconMountain style={{width: rem(20), height: rem(20)}}/>}>
                                Favorite Peaks
                            </Menu.Item>
                            <Menu.Item onClick={() => {
                                onNavigate('My Account')
                            }} leftSection={<IconTemperature style={{width: rem(20), height: rem(20)}}/>}>
                                Weather Preferences
                            </Menu.Item>
                            <Menu.Item onClick={() => {
                                onNavigate('Settings')
                            }} leftSection={<IconInfoCircle style={{width: rem(20), height: rem(20)}}/>}>
                                About
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
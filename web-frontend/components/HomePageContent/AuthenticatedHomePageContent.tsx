'use client';
import dynamic from 'next/dynamic';
import {useAuthContext} from "@/app/context/AuthContext";
import {AppShell, Card, Center, Divider, Group, Image, Space, Stack, Text, Title} from '@mantine/core';
import {useEffect, useState} from "react";
import {NavigationProps} from "@/app/util/types";


const DynamicAuthenticatedNavBar = dynamic(() => import("@/components/DynamicNavbar/AuthenticatedNavBar"), {
  ssr: false
});

export default function AuthenticatedHomePageContent({onNavigate}: NavigationProps) {
    const auth = useAuthContext();
    const [isMounted, setIsMounted] = useState(false);

    useEffect(() => {
        setIsMounted(true);
    }, []);

    if (!isMounted) {
        return null; // or a loading placeholder
    }

    return (
        <>
            <AppShell
                header={{height: 70}}
                padding="md">
                <AppShell.Header>
                    <DynamicAuthenticatedNavBar onNavigate={onNavigate} onHomeClick={() => {}}/>
                </AppShell.Header>
                <AppShell.Main>
                    {auth.user && auth.user.role === "ADMIN" && (
                        <>
                           <Text>Hello {auth.user.email}</Text>
                        </>
                    )}
                </AppShell.Main>
            </AppShell>
        </>
    );
}

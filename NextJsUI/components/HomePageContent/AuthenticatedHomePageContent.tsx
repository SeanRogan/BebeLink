'use client';
import {AppShell} from '@mantine/core';
import React from "react";
import {useAppContext} from "../../app/context/ApplicationContext";
import {NavigationProps} from "../../app/util/types";
import AuthenticatedNavBar from "../DynamicNavBar/AuthenticatedNavBar";

export default function AuthenticatedHomePageContent({onNavigate}: NavigationProps) {

    const app = useAppContext();

    return (
        <>
            <AppShell
                header={{height: 70}}
                padding="md">
                <AppShell.Header>
                    <AuthenticatedNavBar onNavigate={onNavigate}/>
                </AppShell.Header>
                <AppShell.Main>
                    {app.user && (
                        <>

                        </>
                    )}
                </AppShell.Main>
            </AppShell></>);
}
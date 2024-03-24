'use client';
import {AppShell, TextInput} from "@mantine/core";
import React, {useEffect} from "react";

import '@mantine/carousel/styles.css';
import {useAppContext} from "../../app/context/ApplicationContext";
import {NavigationProps} from "../../app/util/types";
import DefaultNavBar from "../DynamicNavBar/DefaultNavBar";

export default function DefaultHomePageContent({onNavigate}: NavigationProps) {

    const app = useAppContext();
    const checkForLogin = () => {
        if (app.user) {
            onNavigate('authenticatedHome');
        }
    }
    useEffect(() => {
        checkForLogin();
    }, [checkForLogin]);


    return (<>{checkForLogin && (<>
            <AppShell
                header={{height: 70}}
                padding="md">
                <AppShell.Header>
                    <DefaultNavBar onNavigate={onNavigate}/>
                </AppShell.Header>
                <AppShell.Main>
                    <Text>Share!</Text>
                    <Text>Engage!</Text>
                    <Text>Promote!</Text>
                </AppShell.Main>
            </AppShell></>)}</>
    );
}
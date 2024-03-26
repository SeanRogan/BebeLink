'use client';
import {AppShell, Button, Center, Divider, Group, Space, Stack, Title} from "@mantine/core";
import React, {useEffect, useState} from "react";
import {useAuthContext} from "@/app/context/AuthContext";
import DefaultNavBar from "@/components/DynamicNavbar/DefaultNavBar";

import '@mantine/carousel/styles.css';
import {NavigationProps} from "@/app/util/types";

export default function DefaultHomePageContent({onNavigate}: NavigationProps) {

    const auth = useAuthContext();
    const [demoActive, setDemoActive] = useState(false);
    const [buttonVisible, setButtonVisible] = useState(true);
    const checkForLogin = () => {
        if (auth.user) {
            onNavigate('authenticatedHome');
        }
    }
    useEffect(() => {
        checkForLogin();
    }, [checkForLogin]);

    const handleBtnClick = () => {
        //implement logic to save field data and move on to account signup,
    }

    return (<>{checkForLogin && (<>
            <AppShell
                header={{height: 70}}
                padding="md">
                <AppShell.Header>
                    <DefaultNavBar onNavigate={onNavigate}/>
                </AppShell.Header>
                <AppShell.Main>

                        <Title order={3} size="7rem">Share!</Title>
                        <Title order={2} size="9rem">Engage!</Title>
                        <Title order={1} size="12rem">Connect!</Title>

                    <Divider/>
                    {/*LINK DEMO CARD COMPONENT*/}
                    <Divider/>
                    {/*PRICING CARD*/}
                    <Space h={"lg"}/>

                    <Divider/>
                    <Space h={"lg"}/>
                        <Center>
                            <>
                                <Button onClick={() => handleBtnClick()}>
                                    Claim your link!
                                </Button>
                                <Space h={"xl"}/>
                            </>
                        </Center>

                </AppShell.Main>
            </AppShell></>)}</>
    );
}
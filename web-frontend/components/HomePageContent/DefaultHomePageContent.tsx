'use client';
import {AppShell, Button, Center, Container, Divider, Grid, Space, Title, Text} from "@mantine/core";
import React, {useEffect, useState} from "react";
import {useAuthContext} from "@/app/context/AuthContext";
import DefaultNavBar from "@/components/DynamicNavbar/DefaultNavBar";
import {NavigationProps} from "@/app/util/types";
import HomePageLinkGen from "@/components/LinkGenerator/HomePageLinkGen";
import { useMediaQuery } from '@mantine/hooks';
import { Footer } from "../Footer/Footer";

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

    const largeScreen = useMediaQuery('(min-width: 992px)');

    return (
        <>
            {checkForLogin && (
                <AppShell header={{height: 70}} padding="md">
                    <AppShell.Header>
                        <DefaultNavBar onNavigate={onNavigate}/>
                    </AppShell.Header>
                    <AppShell.Main 
                        style={{ 
                            backgroundImage: 'url("/background-image.png")',
                            backgroundSize: 'cover',
                            backgroundPosition: 'center',
                        }}
                    >
                        <Container size="lg">
                            <Grid gutter="xl" align="center">
                                <Grid.Col md={12}>
                                    <div style={{ 
                                        background: 'linear-gradient(135deg, #4263EB, #9967FF)', // Changed to a gradient
                                        padding: '2rem',
                                        borderRadius: '1rem',
                                    }}>
                                        <Title order={2} size="3rem" align="center" style={{ color: 'white' }}> 
                                            Shorten a Long URL
                                        </Title>
                                        <Text align="center" style={{ color: 'white' }}> 
                                            Get a short, random URL for your long URL.
                                        </Text>
                                        <Space h="md" />
                                        <HomePageLinkGen />
                                    </div>
                                </Grid.Col>
                            </Grid>
                            <Space h="xl" />
                            <Grid gutter="xl" align="center">
                                <Grid.Col md={12}>
                                    <Title order={1} size={largeScreen ? "4rem" : "3rem"} align="center">
                                        Claim Your Custom URL
                                    </Title>
                                    <Text align="center">
                                        Assign your own custom URL tail for your shortened links.
                                    </Text>
                                    <Space h="md" />
                                    <Center>
                                        <Button size="xl" onClick={() => handleBtnClick()}>
                                            Claim your link today!
                                        </Button>
                                    </Center>
                                </Grid.Col>
                            </Grid>
                            <Space h="xl" />
                            <Grid gutter="xl" align="center">
                                <Grid.Col md={12}>
                                    <Title order={1} size={largeScreen ? "4rem" : "3rem"} align="center">
                                        Pricing Plans
                                    </Title>
                                    <Text align="center">
                                        Choose a plan that fits your needs and budget.
                                    </Text>
                                    <Space h="md" />
                                    <div style={{ display: 'flex', flexDirection: largeScreen ? 'row' : 'column', gap: '1rem' }}>
                                        <div style={{ flex: 1, textAlign: 'center', padding: '2rem', borderRadius: '1rem', border: '1px solid rgba(78, 103, 229, 0.25)', backgroundColor: 'rgba(8, 12, 35, 1)' }}>
                                            <Text color="#4d66e5" size="xl">Basic</Text>
                                            <Title order={2} size="3rem" style={{ margin: '1rem 0', color: '#fff' }}>Free!</Title>
                                            <Text color="#fff">Perfect for personal use and short links.</Text>
                                            <Button color="blue" size="xl" style={{ margin: '1rem 0', width: '100%' }}>Get Started</Button>
                                            <ul style={{ textAlign: 'left', listStyleType: 'none', padding: 0 , color: '#fff' }}>
                                                <li>-Best for content creators and small business customers</li>
                                                <li>-10 free random short links</li>
                                                <li>-Basic link analytics</li>

                                            </ul>
                                        </div>
                                        <div style={{ flex: 1, textAlign: 'center', padding: '2rem', borderRadius: '1rem', border: '1px solid rgba(153, 102, 255, 0.25)', backgroundColor: 'rgba(18, 13, 29, 1)' }}>
                                            <Text color="#9967FF" size="xl">Pro</Text>
                                            <Title order={2} size="3rem" style={{ margin: '1rem 0', color: '#fff'  }}>$3/mo</Title>
                                            <Text color="#fff">Advanced features for power users and small businesses.</Text>
                                            <Button color="grape" size="xl" style={{ margin: '1rem 0', width: '100%' }}>Subscribe</Button>
                                            <ul style={{ textAlign: 'left', listStyleType: 'none', padding: 0 , color: '#fff' }}>
                                                <li>-The best custom links deal on the web!</li>
                                                <li>-100 short links</li>
                                                <li>-Advanced link analytics</li>
                                                <li>-API access</li>
                                            </ul>
                                        </div>
                                        <div style={{ flex: 1, textAlign: 'center', padding: '2rem', borderRadius: '1rem', border: '1px solid rgba(247, 225, 111, 0.25)', backgroundColor: 'rgba(25, 23, 13, 1)' }}>
                                            <Text color="#F7E16F" size="xl">Enterprise</Text>
                                            <Title order={2} size="3rem" style={{ margin: '1rem 0', color: '#fff' }}>Custom</Title>
                                            <Text color="#fff">Tailored solutions for large-scale deployments.</Text>
                                            <Button color="yellow" size="xl" style={{ margin: '1rem 0', width: '100%' }}>Contact Us</Button>
                                            <ul style={{ textAlign: 'left', listStyleType: 'none', padding: 0 , color: '#fff' }}>
                                                <li>-Best for enterprise customers</li>
                                                <li>-Custom branded domains</li>
                                                <li>-Dedicated support</li>
                                                <li>-Volume pricing</li>
                                            </ul>
                                        </div>
                                    </div>
                                </Grid.Col>
                            </Grid>
                            <Space h="xl" />
                        </Container>
                        <Footer />
                    </AppShell.Main>
                </AppShell>
            )}
        </>
    );
}

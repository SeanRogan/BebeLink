/** @jsxImportSource @emotion/react */
'use client';
import {AppShell, Button, Center, Container, Divider, Grid, Space, Title, Text, Loader, Modal, useMantineTheme, List, ThemeIcon, useMantineColorScheme} from "@mantine/core";
import React, {useEffect, useState} from "react";
import {useAuthContext} from "@/app/context/AuthContext";
import DefaultNavBar from "@/components/DynamicNavbar/DefaultNavBar";
import {NavigationProps} from "@/app/util/types";
import HomePageLinkGen from "@/components/LinkGenerator/HomePageLinkGen";
import { useMediaQuery } from '@mantine/hooks';
import { Footer } from "../Footer/Footer";
import { keyframes } from '@emotion/react';
import { IconAlertCircle, IconCheck } from '@tabler/icons-react';
import classes from './HeroImageRight.module.css';
import { css } from '@emotion/react';

export default function DefaultHomePageContent({onNavigate}: NavigationProps) {

    const auth = useAuthContext();
    const [demoActive, setDemoActive] = useState(false);
    const [buttonVisible, setButtonVisible] = useState(true);
    const [generatedLink, setGeneratedLink] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [errorModalOpen, setErrorModalOpen] = useState(false);
    const [linkGenErrorModalOpen, setLinkGenErrorModalOpen] = useState(false);
    const [linkGenErrorMessage, setLinkGenErrorMessage] = useState('');
    const theme = useMantineTheme();
    const { colorScheme } = useMantineColorScheme();

    const checkForLogin = () => {
        if (auth.user) {
            onNavigate('authenticatedHome');
        }
    }
    useEffect(() => {
        checkForLogin();
    }, [checkForLogin]);

    const handleBtnClick = () => {
        if (generatedLink) {
            onNavigate('register');
        } else if (!isLoading) {
            setErrorModalOpen(true);
        }
    }

    const handleLinkGenerated = async (link) => {
        setIsLoading(true);
        try {
            // Simulating an async operation
            await new Promise(resolve => setTimeout(resolve, 1000));
            setGeneratedLink(link);
        } catch (error) {
            handleError('An error occurred while generating the link.');
        } finally {
            setIsLoading(false);
        }
    }

    const handleError = (errorMessage: string) => {
        setLinkGenErrorMessage(errorMessage);
        setLinkGenErrorModalOpen(true);
    };

    const largeScreen = useMediaQuery('(min-width: 992px)');

    const pulseAnimation = keyframes`
        0% {
            box-shadow: 0 0 0 0 rgba(66, 99, 235, 0.7);
        }
        70% {
            box-shadow: 0 0 0 10px rgba(66, 99, 235, 0);
        }
        100% {
            box-shadow: 0 0 0 0 rgba(66, 99, 235, 0);
        }
    `;
    const heroGradientStyle = css`
    position: relative;
    &::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        height: 150px; // Adjust this value to control the height of the blur effect
        background: linear-gradient(
            to bottom,
            ${colorScheme === 'dark' ? 'rgba(26,27,30,0)' : 'rgba(255,255,255,0)'} 0%,
            ${colorScheme === 'dark' ? 'rgba(26,27,30,0.8)' : 'rgba(255,255,255,0.8)'} 100%
        );
        backdrop-filter: blur(10px);
        -webkit-backdrop-filter: blur(10px);
        mask-image: linear-gradient(to bottom, transparent, black);
        -webkit-mask-image: linear-gradient(to bottom, transparent, black);
        pointer-events: none;
    }
`;

    return (
        <>
            {checkForLogin && (
                <AppShell header={{height: 70}}>
                    <AppShell.Header>
                        <DefaultNavBar onNavigate={onNavigate}/>
                    </AppShell.Header>
                    <AppShell.Main>
                        {/* Modified hero section */}
                        <div 
                            className={classes.root} 
                            css={heroGradientStyle}
                            style={{ 
                                backgroundImage: `url("/backgroundimage${colorScheme === 'dark' ? '-dark' : '-light'}.jpg")`,
                                backgroundSize: 'cover',
                                backgroundPosition: 'right',
                                position: 'relative', // Ensure the parent has a relative position
                                overflow: 'hidden', // This is important for the blur effect to work properly
                            }}
                        >
                            <Container size="lg">
                                <div className={classes.inner}>
                                    <div className={classes.content}>
                                        <Title className={classes.title}>
                                            Create{' '}
                                            <Text
                                                component="span"
                                                inherit
                                                variant="gradient"
                                                gradient={{ from: 'blue', to: 'grape' }}
                                            >
                                                custom shortened links
                                            </Text>{' '}
                                            for all your needs
                                        </Title>

                                        <Text className={classes.description} mt={30}>
                                            Whether you're an affiliate marketer, content creator, or just looking to simplify your links, our link shortener service has got you covered. Create custom, branded short links that are easy to remember and share.
                                        </Text>

                                        <List
                                            mt={30}
                                            spacing="sm"
                                            size="sm"
                                            icon={
                                                <ThemeIcon size={20} radius="xl">
                                                    <IconCheck style={{ width: '0.75rem', height: '0.75rem' }} stroke={1.5} />
                                                </ThemeIcon>
                                            }
                                            styles={{
                                                item: {
                                                    color: colorScheme === 'dark' ? theme.colors.dark[2] : theme.black,
                                                },
                                            }}
                                        >
                                            <List.Item>
                                                <b>Custom branded links</b> – create short links with your own custom domain
                                            </List.Item>
                                            <List.Item>
                                                <b>Advanced analytics</b> – track clicks, referrers, locations, and more
                                            </List.Item>
                                            <List.Item>
                                                <b>Easy link management</b> – edit, delete, or expire links at any time
                                            </List.Item>
                                        </List>

                                        <Button
                                            variant="gradient"
                                            gradient={{ from: 'blue', to: 'grape' }}
                                            size="xl"
                                            className={classes.control}
                                            mt={40}
                                            onClick={() => onNavigate('register')}
                                        >
                                            Get started
                                        </Button>
                                    </div>
                                </div>
                            </Container>
                        </div>
                        <Space h="xl" />

                        <Container size="lg">
                            <Grid gutter="xl" align="center">
                                <Grid.Col span={12}>
                                    <div style={{ 
                                        background: 'linear-gradient(135deg, #4263EB, #9967FF)',
                                        padding: '2rem',
                                        borderRadius: '1rem',
                                    }}>
                                        <Title order={2} size="3rem" ta="center" style={{ color: 'white' }}> 
                                            Shorten a Long URL
                                        </Title>
                                        <Text ta="center" style={{ color: 'white' }}> 
                                            Get a short, random URL for your long URL.
                                        </Text>
                                        <Space h="md" />
                                        <HomePageLinkGen 
                                            onLinkGenerated={handleLinkGenerated} 
                                            isLoading={isLoading} 
                                            onError={handleError}
                                        />
                                    </div>
                                </Grid.Col>
                            </Grid>
                            <Space h="xl" />
                            <Grid gutter="xl" align="center">
                                <Grid.Col span={12}>
                                    <Title order={1} size={largeScreen ? "4rem" : "3rem"} ta="center">
                                        Claim Your Custom URL
                                    </Title>
                                    <Text ta="center">
                                        Assign your own custom URL tail for your shortened links.
                                    </Text>
                                    <Space h="md" />
                                    <Center>
                                        <Button 
                                            size="xl" 
                                            onClick={handleBtnClick}
                                            disabled={isLoading}
                                            styles={(theme) => ({
                                                root: {
                                                    animation: generatedLink ? `${pulseAnimation} 2s infinite` : 'none',
                                                    '&:hover': {
                                                        animation: 'none',
                                                    },
                                                },
                                            })}
                                        >
                                            {isLoading ? <Loader color="white" size="sm" /> : "Claim your link today!"}
                                        </Button>
                                    </Center>
                                </Grid.Col>
                            </Grid>
                            <Space h="xl" />
                            <Grid gutter="xl" align="center">
                                <Grid.Col span={12}>
                                    <Title order={1} size={largeScreen ? "4rem" : "3rem"} ta="center">
                                        Pricing Plans
                                    </Title>
                                    <Text ta="center">
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
                        <Modal
                            opened={errorModalOpen}
                            onClose={() => setErrorModalOpen(false)}
                            title={
                                <div style={{ display: 'flex', alignItems: 'center', color: theme.colors.red[6] }}>
                                    <IconAlertCircle size={24} style={{ marginRight: '10px' }} />
                                    <Text size="xl" fw={700}>Oops! 🫢 First reserve your link!</Text>
                                </div>
                            }
                            centered
                            size="md"
                            radius="md"
                            shadow="xl"
                            overlayProps={{
                                color: theme.colorScheme === 'dark' ? theme.colors.dark[9] : theme.colors.gray[2],
                                opacity: 0.55,
                                blur: 3,
                            }}
                            styles={{
                                body: {
                                    padding: '20px',
                                },
                                title: {
                                    marginBottom: '15px',
                                },
                            }}
                        >
                            <Text size="md" style={{ lineHeight: 1.6 }}>
                                Use the &quot;Shorten a Long URL&quot; section to create your custom link!
                            </Text>
                            <Button 
                                fullWidth 
                                mt="md" 
                                onClick={() => setErrorModalOpen(false)}
                                style={{ 
                                    background: 'linear-gradient(135deg, #4263EB, #9967FF)',
                                    transition: 'all 0.3s ease',
                                }}
                                styles={{
                                    root: {
                                        '&:hover': {
                                            transform: 'translateY(-2px)',
                                            boxShadow: theme.shadows.md,
                                        } as React.CSSProperties,
                                    },
                                }}
                            >
                                Got it!
                            </Button>
                        </Modal>
                        <Modal
                            opened={linkGenErrorModalOpen}
                            onClose={() => setLinkGenErrorModalOpen(false)}
                            title={
                                <div style={{ display: 'flex', alignItems: 'center', color: theme.colors.red[6] }}>
                                    <IconAlertCircle size={24} style={{ marginRight: '10px' }} />
                                    <Text size="xl" fw={700}>Oops! 🫢 link generation failed!</Text>
                                </div>
                            }
                            centered
                            size="md"
                            radius="md"
                            shadow="xl"
                            overlayProps={{
                                color: theme.colorScheme === 'dark' ? theme.colors.dark[9] : theme.colors.gray[2],
                                opacity: 0.55,
                                blur: 3,
                            }}
                            styles={{
                                body: {
                                    padding: '20px',
                                },
                                title: {
                                    marginBottom: '15px',
                                },
                            }}
                        >
                            <Text size="md" style={{ lineHeight: 1.6 }}>
                                {linkGenErrorMessage}
                            </Text>
                            <Button 
                                fullWidth 
                                mt="md" 
                                onClick={() => setLinkGenErrorModalOpen(false)}
                                style={{ 
                                    background: 'linear-gradient(135deg, #4263EB, #9967FF)',
                                    transition: 'all 0.3s ease',
                                }}
                                styles={{
                                    root: {
                                        '&:hover': {
                                            transform: 'translateY(-2px)',
                                            boxShadow: theme.shadows.md,
                                        } as React.CSSProperties,
                                    },
                                }}
                            >
                                Got it!
                            </Button>
                        </Modal>
                    </AppShell.Main>
                </AppShell>
            )}
        </>
    );
}

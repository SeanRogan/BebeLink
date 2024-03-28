'use client';
import {AuthProvider} from "@/app/context/AuthContext";
import React, {useState} from "react";
import DefaultHomePageContent from "@/components/HomePageContent/DefaultHomePageContent";
import AuthenticatedHomePageContent from "@/components/HomePageContent/AuthenticatedHomePageContent";
import Login from "@/components/Login/Login";
import Registration from "@/components/Registration/Registration";
import '@mantine/carousel/styles.css';


export default function LandingPage() {
    const [currentPage, setCurrentPage] = useState('home');
    const navigate = (page: string) => {
        setCurrentPage(page);
    }

    return (<>
        <AuthProvider>

                            {currentPage === 'home' && (
                                <DefaultHomePageContent
                                    onNavigate={navigate}
                                />
                            )}
                            {currentPage === 'login' && (
                                <Login
                                    onNavigate={navigate}
                                />
                            )}
                            {currentPage === 'authenticatedHome' && (
                                <AuthenticatedHomePageContent
                                    onNavigate={navigate}
                                />
                            )}
                            {currentPage === 'register' && (
                                <Registration
                                    onNavigate={navigate}
                                />
                            )}

        </AuthProvider>
    </>);
}

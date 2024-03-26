'use client';
import {AuthProvider} from "@/app/context/AuthContext";
import React, {useState} from "react";
import DefaultHomePageContent from "@/components/HomePageContent/DefaultHomePageContent";
import AuthenticatedHomePageContent from "@/components/HomePageContent/AuthenticatedHomePageContent";
import Login from "@/components/Login/Login";
import Registration from "@/components/Registration/Registration";
import FavoritesPage from "@/components/FavoritesPage/FavoritesPage";
import SearchForecastPage from "@/components/Forecast/SearchForecastPage";
import {SearchProvider} from "@/app/context/SearchContext";
import {ForecastProvider} from "@/app/context/ForecastContext";
import '@mantine/carousel/styles.css';
import {UserReportProvider} from "@/app/context/UserReportContext";
import UserForecastPage from "@/components/Forecast/UserForecastPage";
import {FavoritesProvider} from "@/app/context/FavoritesContext";

export default function LandingPage() {
    const [currentPage, setCurrentPage] = useState('home');
    const navigate = (page: string) => {
        setCurrentPage(page);
    }

    return (<>
        <AuthProvider>
            <SearchProvider>
                <ForecastProvider>
                    <UserReportProvider>
                        <FavoritesProvider>

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
                        {currentPage === 'favorites' && (
                            <FavoritesPage
                                onNavigate={navigate}
                            />
                        )}
                        </FavoritesProvider>
                    </UserReportProvider>
                </ForecastProvider>
            </SearchProvider>
        </AuthProvider>
    </>);
}

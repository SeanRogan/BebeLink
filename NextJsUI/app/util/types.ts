import React, {ReactNode} from "react";

export type NavigationProps = {
    onNavigate: (page: string) => void;
}
export type NavbarProps = {
    onNavigate: (page: string) => void;
    onHomeClick: () => void;
}
export type UserProfile = {
    profileId: number;
    maxTemp: number;
    minTemp: number;
    maxWind: number;
    preferLightRain: boolean;
    preferRainShowers: boolean;
    preferModRain: boolean;
    preferRiskTstorm: boolean;
    preferLightSnow: boolean;
    preferSnowShowers: boolean;
    preferHeavySnow: boolean;
    preferClear: boolean;
    preferSomeClouds: boolean;
    preferCloudy: boolean;
    create_date: string; // or Date if you prefer to use Date objects
    last_update: string; // or Date
    favoritePeaks: MountainPeak[]; // replace 'any' with the appropriate type if known
}
export type ProfileChangeRequest = {
    profileId: number;
    maxTemp: number;
    minTemp: number;
    maxWind: number;
    preferLightRain: boolean;
    preferRainShowers: boolean;
    preferModRain: boolean;
    preferRiskTstorm: boolean;
    preferLightSnow: boolean;
    preferSnowShowers: boolean;
    preferHeavySnow: boolean;
    preferClear: boolean;
    preferSomeClouds: boolean;
    preferCloudy: boolean;
}
export type Authority = {
    authority: string;
};
export type User = {
    userId: number;
    username: string;
    email: string;
    password: string;
    create_date: string; // or Date
    last_update: string; // or Date
    role: string;
    profile: UserProfile;
    enabled: boolean;
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    authorities: Authority[];
    credentialsNonExpired: boolean;
}
export type WeatherReport = {}
export type ForecastData = {
    dayOfTheWeek: string;
    peakName: string;
    high: string;
    low: string;
    expectedRainfall: number;
    expectedSnowfall: number;
    weatherConditions: string;
    windConditions: string;
};
export type DailyForecastResponse = {
    forecastData: {
        AM: ForecastData;
        PM: ForecastData;
        Night: ForecastData;
    };
};
export type ExtendedForecastResponse = DailyForecastResponse[];
export type MountainPeak = {
    peakId: number;
    peakName: string;
    homerangeId: number;
    uri: number;
    create_date: string;
    last_update: string;
    homeSubrange: MountainSubRange;
    homeRange: MountainRange;
    weatherReports: WeatherReport[];
    users: UserProfile[];
}
export type MountainRange = {
    rangeId: number;
    rangeName: string;
    uri: string;
    create_date: string;
    last_update: string;
    subRanges: MountainSubRange[];
    peaks: number[];
}
export type MountainSubRange = {
    subrangeId: number;
    homeRangeUri: string;
    rangeName: string;
    uri: string;
    create_date: string; // or use Date type if you prefer
    last_update: string; // or use Date type if you prefer
    peaks: number[]; // Array of numbers representing peak IDs
}
export type ApplicationContextType = {
    user: User | null;
    profile: UserProfile | undefined;
    setProfile: (profile: UserProfile) => void;
    updateProfile: () => void;
    loginUser: (credentials: { email: string; password: string }) => Promise<any>;
    registerUser: (credentials: { username: string; email: string; password: string; role: string; }) => Promise<any>;
    logoutUser: () => void;
}
export type AppCtxProviderProps = {
    children: ReactNode;
}

import DefaultHomePageContent from "../components/HomePageContent/DefaultHomePageContent";
import AuthenticatedHomePageContent from "../components/HomePageContent/AuthenticatedHomePageContent";
import Registration from "../components/Registration/Registration";
import Login from "../components/Login/Login";
import {useState} from "react";
import {ApplicationContextProvider} from "./context/ApplicationContext";

export default function HomePage() {

  const [currentPage, setCurrentPage] = useState('home');
  const navigate = (page: string) => {
    setCurrentPage(page);
  }

  return (<>
  <ApplicationContextProvider>
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
  </ApplicationContextProvider>
  </>);
}

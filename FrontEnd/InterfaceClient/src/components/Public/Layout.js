import React from 'react';
import Header from '../../components/Public/Header/Header'
import Footer from '../../components/Public/Footer/Footer'
import { Outlet } from 'react-router-dom';

const Layout = () => {
    return (
        <div>
            <Header/>
            <Outlet/>
            <Footer/>
        </div>
    );
};

export default Layout;
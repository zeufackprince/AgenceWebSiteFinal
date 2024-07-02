import React from 'react';
import './ALayout.css'
import { Outlet } from 'react-router-dom';
import DashMenu from '../Admin/DashMenu/DashMenu'
const ALayout = () => {
    return (
        <div className='ALayout'>
            <DashMenu/>
            <Outlet/>
        </div>
    );
};

export default ALayout;
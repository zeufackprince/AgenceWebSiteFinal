import React from 'react';
import { Route,Routes } from 'react-router-dom';
import ALayout from './ALayout';
import DashMain from '../../pages/Admin/DashMain/DashMain'
import MainList from '../../pages/Admin/crud-app-clients/MainList';

const AdminRouter = () => {
    return (
         <Routes>
            <Route element ={<ALayout/>}>
                <Route index element ={<DashMain/>}/>
                <Route path='/dashboard' element ={<DashMain/>}/>
                <Route path='/crud-client' element={<MainList/>}/>
            </Route>
        </Routes>
        
        
    );
};

export default AdminRouter;
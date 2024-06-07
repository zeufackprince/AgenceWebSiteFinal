import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState({ user: null, role: null });

    const handleLogin = (token, user, role) => {
        setAuth({ user, role });
        localStorage.setItem('authToken', token);
    };

    const handleLogout = () => {
        setAuth({ user: null, role: null });
        localStorage.removeItem('authToken');
    };

    const getRole = () => auth.role;

    return (
        <AuthContext.Provider value={{ ...auth, handleLogin, handleLogout, getRole }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};

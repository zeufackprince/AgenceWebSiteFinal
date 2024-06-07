import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthProvider";

const ProtectedRoute = ({ role, children }) => {
    const auth = useAuth();

    if (!auth.user) {
        return <Navigate to="/auth/login" />;
    }

    if (role && auth.role !== role) {
        return <Navigate to="/" />;
    }

    return children;
};

export default ProtectedRoute;

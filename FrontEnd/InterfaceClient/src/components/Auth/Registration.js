import React, { useState } from "react";
import { registerUser } from "../utils/ApiFunctions";
import { Link } from "react-router-dom";

const Registration = () => {
    const [registration, setRegistration] = useState({
        name: "",
        email: "",
        password: "",
        telephone: "",
        image: "",
        role: ""
    });

    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    const handleInputChange = (e) => {
        setRegistration({ ...registration, [e.target.name]: e.target.value });
    };

    const handleRegistration = async (e) => {
        e.preventDefault();
        try {
            const result = await registerUser(registration);
            setSuccessMessage(result);
            setErrorMessage("");
            setRegistration({ name: "", email: "", password: "", telephone: "", image: "", role: "" });
        } catch (error) {
            setSuccessMessage("");
            setErrorMessage(`Registration error: ${error.message}`);
        }
        setTimeout(() => {
            setErrorMessage("");
            setSuccessMessage("");
        }, 5000);
    };

    return (
        <section className="container col-6 mt-5 mb-5">
            {errorMessage && <p className="alert alert-danger">{errorMessage}</p>}
            {successMessage && <p className="alert alert-success">{successMessage}</p>}

            <h2>Register</h2>
            <form onSubmit={handleRegistration}>
                <div className="mb-3 row">
                    <label htmlFor="name" className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-10">
                        <input
                            id="name"
                            name="name"
                            type="text"
                            className="form-control"
                            value={registration.name}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="mb-3 row">
                    <label htmlFor="email" className="col-sm-2 col-form-label">Email</label>
                    <div className="col-sm-10">
                        <input
                            id="email"
                            name="email"
                            type="email"
                            className="form-control"
                            value={registration.email}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="mb-3 row">
                    <label htmlFor="password" className="col-sm-2 col-form-label">Password</label>
                    <div className="col-sm-10">
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            name="password"
                            value={registration.password}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="mb-3 row">
                    <label htmlFor="telephone" className="col-sm-2 col-form-label">Tel</label>
                    <div className="col-sm-10">
                        <input
                            id="telephone"
                            name="telephone"
                            type="number"
                            className="form-control"
                            value={registration.telephone}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="mb-3 row">
                    <label htmlFor="role" className="col-sm-2 col-form-label">Role</label>
                    <div className="col-sm-10">
                        <input
                            id="role"
                            name="role"
                            type="text"
                            className="form-control"
                            value={registration.role}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="mb-3 row">
                    <label htmlFor="image" className="col-sm-2 col-form-label">Profile image</label>
                    <div className="col-sm-10">
                        <input
                            id="image"
                            name="image"
                            type="file"
                            className="form-control"
                            value={registration.image}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="mb-3">
                    <button type="submit" className="btn btn-hotel" style={{ marginRight: "10px" }}>
                        Register
                    </button>
                    <span style={{ marginLeft: "10px" }}>
                        Already have an account? <Link to={"/auth/login"}>Login</Link>
                    </span>
                </div>
            </form>
        </section>
    );
};

export default Registration;

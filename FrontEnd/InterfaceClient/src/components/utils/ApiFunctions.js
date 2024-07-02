// BackEnd simulé
const users = [
    { email: 'admin@example.com', password: 'admin', role: 'admin' },
    { email: 'client@example.com', password: 'client', role: 'client' }
];

export const loginUser = async ({ email, password }) => {
    const user = users.find(user => user.email === email && user.password === password);

    if (user) {
        return {
            token: 'fake-jwt-token',
            user: { email: user.email },
            role: user.role
        };
    } else {
        return null;
    }
};

export const registerUser = async (newUser) => {
    const userExists = users.some(user => user.email === newUser.email);

    if (userExists) {
        throw new Error('User already exists');
    }

    users.push(newUser);
    return 'Registration successful';
};

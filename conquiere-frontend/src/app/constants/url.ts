export const HOME = {
    base: ''
};

export const AUTHENTIFICATION = {
    base: 'auth',
    login: 'login',
    registration: 'registration'
}
export const USER = {
    base: 'user',
    dashbord: 'dashboard'
}

export const AUTH_URL = {
    login: `/${AUTHENTIFICATION.base}/${AUTHENTIFICATION.login}`,
    register: `/${AUTHENTIFICATION.base}/${AUTHENTIFICATION.registration}`,
};

export const USER_URL = {
    dashboard: `/${USER.base}/${USER.dashbord}`,
};

export const HOME = {
    base : ''
};


export const AUTHENTIFICATION = {
    base : 'auth',
    login : 'login',
    registration :'registration'
}


export const AUTH_URL = {
    login : `/${AUTHENTIFICATION.base}/${AUTHENTIFICATION.login}`,
    register : `/${AUTHENTIFICATION.base}/${AUTHENTIFICATION.registration}`,
};

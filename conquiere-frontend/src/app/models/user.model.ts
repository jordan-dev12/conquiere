export interface User {
    id?: number,
    name: string,
    surname: string,
    email: string,
    password: string,
    birthdate: string,
    roles?: string[]
}

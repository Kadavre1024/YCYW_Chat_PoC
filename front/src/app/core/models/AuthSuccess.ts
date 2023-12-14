import { Authority } from "./Authority";

export interface AuthSuccess{
    token:string;
    type:string;
    id:number;
    username:string;
    firstName:string;
    laststName:string;
    password:string;
    authorities: Authority[];
}
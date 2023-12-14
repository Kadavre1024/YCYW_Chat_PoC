export interface Message {
    id:number;
    discussionId: number;
    clientUserId: number;
    supportUserId: number;
    message: string;
    createdAt:Date;
}
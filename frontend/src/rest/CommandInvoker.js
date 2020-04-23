import QuestionCommandHandler from "./QuestionCommandHandler";

export default class CommandInvoker{
    constructor(authorization){
        this.authorization = authorization;
        this.questionCommandHandler = new QuestionCommandHandler(authorization);
    }

    invokeCommand(commandRequest){
        if(commandRequest in this.questionCommandHandler.requests){
            return this.questionCommandHandler.requestToCommandMapper[commandRequest];
        }
    }
}

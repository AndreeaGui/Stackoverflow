import Command from "./Command";

const BASE_URL = "http://localhost:8080";
export default class QuestionCommandHandler{
    constructor(authorization){
        this.authorization = authorization;
        this.requests = {
            handleListAllQuestions:"handleListAllQuestions",
            handleFilterQuestionsByTitle:"handleFilterQuestionsByTitle",
            handleFilterQuestionsByTag:"handleFilterQuestionsByTag",
            handleCreateQuestion:"handleCreateQuestion",
        };
        this.requestToCommandMapper = {
            [this.requests.handleListAllQuestions]:this.handleListAllQuestions(),
            [this.requests.handleFilterQuestionsByTitle]:this.handleFilterQuestionsByTitle(),
            [this.requests.handleFilterQuestionsByTag]:this.handleFilterQuestionsByTag(),
            [this.requests.handleCreateQuestion]:this.handleCreateQuestion(),
        };
    }

    handleListAllQuestions(){
        const execution = ()=> fetch(BASE_URL + "/questions", {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => {
            if(response.status===200)
                return response.json();
            return {"status": response.status, "text": response.text()};
        });
        return new Command(execution);
    }


    handleFilterQuestionsByTitle(){
        const execution = (content)=>fetch(BASE_URL + "/questions/title/"+content.title, {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => {
            if(response.status===200)
                return response.json();
            return {"status": response.status, "text": response.text()};
        });
        return new Command(execution);
    }

    handleFilterQuestionsByTag(){
        
        const execution = (content)=> fetch(BASE_URL + "/questions/tag/"+content.tagName, {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => {
            if(response.status===200)
                return response.json();
            return {"status": response.status, "text": response.text()};
        });
        return new Command(execution);
    }

    handleCreateQuestion() {
        const execution = (content)=>fetch(BASE_URL + "/questions", {
            method: "POST",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "title": content.title,
                "text": content.text,
                "tags": content.tags
            })
        }).then(response => {
            if(response.status===200)
                return response.json();
            return {"status": response.status, "text": response.text()};
        });
        return new Command(execution);
    }
}
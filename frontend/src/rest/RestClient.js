import CommandInvoker from "./CommandInvoker";

const BASE_URL = "http://localhost:8080";

class RestClient{
    constructor(){
        this.username = "";
        this.password = "";
        this.authorization="None";
        this.commandInvoker = new CommandInvoker(this.authorization);
    }

    authenticate(username, password){
        this.username = username;
        this.password = password;
        this.authorization = "Basic "+btoa(username + ":" + password);
        this.commandInvoker = new CommandInvoker(this.authorization);
    }

    logout(){
        this.username = "";
        this.password = "";
        this.authorization="None";
        this.commandInvoker = new CommandInvoker(this.authorization);
    }

    handleQuestionCommand(commandRequest){
        return this.commandInvoker.invokeCommand(commandRequest);
    }

    handleListAnswersByQuestion(content){
        return fetch(BASE_URL + "/question/"+content.questionId+"/answers", {
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => {
            if(response.status===200)
                return response.json();
            return {"status": response.status, "text": response.text()};
        });
    }

    handleCreateAnswer(content){
        return fetch(BASE_URL + "/question/"+content.questionId+"/answers", {
            method: "POST",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "text": content.text
            })
        }).then(response => {
            if(response.status===200)
                return response.json();
            return {"status": response.status, "text": response.text()};
        });
    }

    handleUpdateAnswer(content){
        return fetch(BASE_URL + "/question/"+content.questionId+"/answers/"+content.answerId, {
            method:"PUT",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "text": content.text
            })
        }).then(response=>{
            if(response.status === 200)
                return response.json();
            return {"status": response.status, "text": response.text()}; 
        });
    }

    handleDeleteAnswer(content){
        debugger;
        return fetch(BASE_URL + "/question/"+content.questionId+"/answers/"+content.answerId, {
            method:"DELETE",
            headers: {
                "Authorization": this.authorization,
            }
        }).then(response=>{
            debugger;
            if(response.status === 200)
                return "OK";
            return "NO";
        });
    }

    handleCreateQuestionVote(content){
        return fetch(BASE_URL + "/questions/"+content.questionId+"/vote?isUpVote="+content.type, {
            method: "POST",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => {
            if(response.status===200)
                return; //mesaj de ok sau not ok
            return {"status": response.status, "text": response.text()};
        });
    }

    handleCreateAnswerVote(content){
        return fetch(BASE_URL+ "/question/"+content.questionId+"/answers/"+content.answerId+"/vote?isUpVote="+content.type, {
            method: "POST",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => {
            if(response.status===200)
                return; //mesaj de ok sau not ok
            return {"status": response.status, "text": response.text()};
        });
    }

    handleListAnswersByScore(questionId){
        return fetch(BASE_URL+"/question/"+questionId+"/answers-by-score",{
            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => {
            if(response.status===200)
                return response.json();
            return {"status": response.status, "text": response.text()};
        });
    }
}

const restClient = new RestClient();
restClient.authenticate("ana","000");
export default restClient;
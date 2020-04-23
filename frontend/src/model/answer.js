
import { EventEmitter } from "events";
import restClient from "../rest/RestClient";

class Answer extends EventEmitter {
    constructor() {
        super();
        this.state = {
            answers: [],
            //object used to fill the values from the input
            newAnswer: {
                answerId: "",
                userId: "",
                questionId: "",
                text: "",
            },
            currentQuestionAnswers: [],
            updateAnswerText: "",
            answerVotes: [],
            answersByScore: []

        };
    }


    addAnswer(questionId, text) {
        const content = {"questionId": questionId, "text":text};
        restClient.handleCreateAnswer(content).then(response=>{
            this.state = {
                ...this.state,
                answers: this.state.answers.concat(
                    [response]
                )
    
            };
            this.emit("change", this.state);
        });

    }

    changeNewAnswerProperty(property, value) {
        this.state = {
            ...this.state,
            newAnswer: {
                ...this.state.newAnswer,
                [property]: value
            }

        };

        this.emit("change", this.state);
    }

    deleteAnswer(answerId) {
        debugger;
        const thisAnswer = this.state.currentQuestionAnswers.filter(x=>x.answerId===answerId)[0];
        if(typeof thisAnswer === 'undefined' || thisAnswer === null)
            return;
        const content = {"questionId":thisAnswer.questionId, "answerId": answerId};
        restClient.handleDeleteAnswer(content).then(response=>{
            debugger;
            this.state = {
                ...this.state,
                answers: this.state.answers.filter(x=>x.answerId!==answerId),
                currentQuestionAnswers: this.state.currentQuestionAnswers.filter(x=>x.answerId!==answerId)
    
            };
            this.emit("change", this.state);
        });

    }

    updateAnswer(answerId, text) {
        debugger;
        const content = {"answerId": answerId, "text":text};
        restClient.handleUpdateAnswer(content).then(response =>{
            let thisAnswer = this.state.answers.filter(x=>x.answerId!==response.answerId)[0];
            thisAnswer = {
                answerId: response.answerId,
                text: response.text
            }
            this.state.answers = this.state.answers.filter(x=>x.answerId!==response.answerId);
            this.state.currentQuestionAnswers = this.state.answers.filter(x=>x.answerId!==response.answerId);
            this.state = {
                ...this.state,
                answers: this.state.answers.concat([thisAnswer]),
                currentQuestionAnswers: this.state.currentQuestionAnswers.concat([thisAnswer])
            };
        });
            
        this.emit("change", this.state);
    }


    listAnswersByQuestion(questionId) {
        //let intId = parseInt(questionId);
        const content = {"questionId": questionId};
        restClient.handleListAnswersByQuestion(content).then(response=>{
            this.state = {
                ...this.state,
                currentQuestionAnswers: response
    
            };
            this.emit("change", this.state);
        });
    }

    addAnswerVote(questionId, answerId, type) {
        const content = {"questionId": questionId, "answerId": answerId, "type": type};
        restClient.handleCreateAnswerVote(content).then(response=>{

            this.state = {
                ...this.state,
                answerVotes: this.state.answerVotes.concat(response)
    
            };
            this.emit("change", this.state);
        });
        
    }

    listAnswersByScore(questionId){
        restClient.handleListAnswersByScore(questionId).then(response=>{
            this.state = {
                ...this.state,
                answersByScore: response
    
            };
            this.emit("change", this.state);
        });
        window.location.assign("#/answers-by-score");
    }
}

const answer = new Answer();

export default answer;

import { EventEmitter } from "events";
import restClient from "../rest/RestClient";

class Vote extends EventEmitter {
    constructor() {
        super();
        this.state = {

            questionVotes: [],
            answerVotes: []

        };
    }


    addQuestionVote(questionId, type) {
        const content = {"questionId": questionId, "type": type};
        restClient.handleCreateQuestionVote(content).then(response=>{

            this.state = {
                ...this.state,
                questionVotes: this.state.questionVotes.concat(response)
    
            };
            this.emit("change", this.state);
        });
        
    }

    addAnswerVote(answerId, type) {
        this.state = {
            ...this.state,
            answerVotes: this.state.answerVotes.concat(
                [{
                    userId: 1,
                    answerId: answerId,
                    type: type
                }]
            )

        };
        this.emit("change", this.state);
    }

    computeAnswerScore(answerId) {
        let votes = this.state.answerVotes;
        let score = 0;
        for (let i = 0; i < votes.length; i++) {
            if (votes[i].answerId === answerId) {
                score = votes[i].type ? (score + 1) : (score - 1);
            }
        }
        return score;
    }


}

const vote = new Vote();

export default vote;
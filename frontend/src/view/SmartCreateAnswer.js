import React, { Component } from "react";
import CreateAnswer from "./CreateAnswer";
import answer from "../model/answer";
import answerPresenter from "../presenter/answerPresenter"


const mapModelStateToComponentState = modelState => ({
    newAnswerId: modelState.newAnswer.answerId,
    newUserId: modelState.newAnswer.userId,
    newText: modelState.newAnswer.text,
    newQuestionId: modelState.newAnswer.questionId
});

export default class SmartCreateAnswer extends Component {
    constructor() {
        super();
        this.state = mapModelStateToComponentState(answer.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        answer.addListener("change", this.listener);
    }

    componentWillUnmount() {
        answer.removeListener("change", this.listener);
        //prevent memory leacks
    }

    //the render method - shows the things
    render() {
        return (
            <CreateAnswer
                onCreate={answerPresenter.onCreate}
                onChange={answerPresenter.onChange}
                newUserId={this.state.newUserId}
                newQuestionId={this.state.newQuestionId}
                newText={this.state.newText}
            />
        );
    }
}
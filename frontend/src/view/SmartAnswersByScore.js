import React, { Component } from "react";
import answer from "../model/answer";
import answerPresenter from "../presenter/answerPresenter";
import DumbAnswersList from "./DumbAnswersList";



const mapModelStateToComponentState = modelState => ({
    answers: modelState.answersByScore

});

export default class SmartAnswersByScore extends Component {
    constructor() {
        super();
        answerPresenter.refreshAnswerList();
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
            <DumbAnswersList
            answers={this.state.answers}
            onDelete={answerPresenter.onDelete}
            onUpdate={answerPresenter.onUpdate}
            onAnswerVoteUp={answerPresenter.onAnswerVoteUp}
            onAnswerVoteDown={answerPresenter.onAnswerVoteDown} />
        );
    }
}
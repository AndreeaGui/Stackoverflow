import React, { Component } from "react";
import CreateQuestion from "./CreateQuestion";
import question from "../model/question";
import createQuestionPresenter from "../presenter/createQuestionPresenter"


const mapModelStateToComponentState = modelState => ({
    newUserId: modelState.newQuestion.userId,
    newTitle: modelState.newQuestion.title,
    newText: modelState.newQuestion.text,
    newTags: modelState.newQuestion.tags
});

export default class SmartCreateQuestion extends Component {
    constructor() {
        super();
        this.state = mapModelStateToComponentState(question.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        question.addListener("change", this.listener);
    }

    componentWillUnmount() {
        question.removeListener("change", this.listener);
        //prevent memory leacks
    }

    //the render method - shows the things
    render() {
        return (
            <CreateQuestion
                onCreate={createQuestionPresenter.onCreate}
                onChange={createQuestionPresenter.onChange}
                newUserId={this.state.newUserId}
                newTitle={this.state.newTitle}
                newText={this.state.newText}
                newTags={this.state.newTags} />
        );
    }
}
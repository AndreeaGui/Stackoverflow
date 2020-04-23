import React, { Component } from "react";
import QuestionDetails from "./QuestionDetails";
import question from "../model/question";
import answer from "../model/answer";
import vote from "../model/vote";
import answerPresenter from "../presenter/answerPresenter";
import votePresenter from "../presenter/votePresenter";
import questionsListPresenter from "../presenter/questionsListPresenter";

const mapModelState = (questionModelState, voteModelState, answerModelState, props) => ({
    question: questionModelState.questions.filter(x=>x.questionId===parseInt(props.match.params.index))[0],
    answer: answerModelState.answers,
    questionVote: voteModelState.questionVotes,
    answerVote: voteModelState.answerVotes,
    currentAnswers: answerModelState.currentQuestionAnswers,
    updateAnswerText: answerModelState.updateText

});

export default class SmartQuestionDetails extends Component {
    constructor(props) {
        super(props);
        questionsListPresenter.refreshQuestionList();
        answerPresenter.refreshQuestionAnswers(this.props.match.params.index);
        this.state = mapModelState(question.state, vote.state, answer.state, this.props);
        this.listener = () => this.setState(mapModelState(question.state, vote.state, answer.state, this.props));
        question.addListener("change", this.listener);
        answer.addListener("change", this.listener);
        vote.addListener("change", this.listener);
    }

    componentWillUnmount() {
        question.removeListener("change", this.listener);
        answer.removeListener("change", this.listener);
        vote.removeListener("change", this.listener);

    }

    componentDidUpdate(prev) {
        if (prev.match.params.index !== this.props.match.params.index) {
            this.setState(mapModelState(question.state, vote.state, answer.state, this.props));
        }
    }

    //the render method - shows the things
    render() {
        return (
            <QuestionDetails
                title={this.state.question.title}
                text={this.state.question.text}
                tags={this.state.question.tags}
                dateTime={this.state.question.dateTime}
                upVotes={this.state.question.upVotes}
                downVotes={this.state.question.downVotes}
                questionIndex={this.props.match.params.index}
                answerUpdateText={this.state.answer.updateAnswerText}
                answers={this.state.currentAnswers}
                onDelete={answerPresenter.onDelete}
                onUpdate={answerPresenter.onUpdate}
                onAddAnswer={answerPresenter.onAddAnswer}
                onAnswerVoteDown={votePresenter.onAnswerVoteDown}
                onAnswerVoteUp={votePresenter.onAnswerVoteUp}
                onQuestionVoteDown={votePresenter.onQuestionVoteDown}
                onQuestionVoteUp={votePresenter.onQuestionVoteUp}
                onListAnswersByScore={answerPresenter.onListAnswersByScore}

            />
        );
    }
}
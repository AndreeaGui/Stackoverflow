import React from "react"
import DumbAnswersList from "./DumbAnswersList";

const QuestionDetails = ({ userId, title, text, tags, dateTime, upVotes, downVotes,
    questionIndex, answerUpdateText,
    answers, onDelete, onUpdate, onAddAnswer,
    onAnswerVoteUp, onAnswerVoteDown, onQuestionVoteUp, onQuestionVoteDown, onListAnswersByScore }) => (
        <div className="container has-background-primary">
            <h2 className="title is-centerd">Question {questionIndex}</h2>
            <label class="subtitle">User: </label>
            <span>{userId}</span>
            <br />
            <label >Title: </label>
            <span>{title}</span>
            <br />
            <label>Text: </label>
            <span>{text}</span>
            <br />
            <label>Date: </label>
            <span>{dateTime}</span>
            <br />
            <label>Votes Up: </label>
            <span>{upVotes}</span>
            <br />
            <label>Votes Down: </label>
            <span>{downVotes}</span>
            <br />
            <div className="container">
                <ol class="ol" type="a" >{
                    tags.map((tag, index) => (
                        <li key={index}>
                            {tag}
                        </li>
                    ))}
                </ol>
            </div>
            <br />
            <div>
                <button class="button is-success is-inverted is-outlined"
                    onClick={() => onQuestionVoteUp(questionIndex)}>
                    Vote UP
                </button>
                <button class="button is-danger is-inverted is-outlined"
                    onClick={() => onQuestionVoteDown(questionIndex)}>
                    Vote DOWN
                </button>
            </div>
            <br />
            <button class="button is-outlined"
                onClick={() => onListAnswersByScore(questionIndex)}>
            List answers by score</button>
            <br />
            <br />
            <button class="button is-outlined"
                onClick={onAddAnswer}>Add answer</button>
            <br />
            <DumbAnswersList
                //questionIndex={questionIndex}
                answers={answers}
                onDelete={onDelete}
                onUpdate={onUpdate}
                onAnswerVoteUp={onAnswerVoteUp}
                onAnswerVoteDown={onAnswerVoteDown}

            />
            <br />
            <label>Update Answer Text: </label>
            <input class="input" type="text" placeholder="Update text for answer"
                value={answerUpdateText} onChange={e => onUpdate("answerUpdateText", e.target.value)} />
            <br />
        </div>

    );

export default QuestionDetails;
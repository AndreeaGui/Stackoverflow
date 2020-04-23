
import React from "react";
import "bulma/css/bulma.css"


const DumbQuestionsList = ({ questions, onViewTags }) => (
    <div className="container has-background-light">
        <h2>Questions</h2>
        <table border="1" className="table is-bordered is-hoverable is-stripped is-narrow is-fullwidth 
        has-background-white-ter has-text-dark">
            <thead>
                <tr>
                    <th>User</th>
                    <th>Title</th>
                    <th>Text</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {
                    questions.map((question, index) => (
                        <tr key={index} data-cy="question">
                            <td>{question.userId}</td>
                            <td>{question.title}</td>
                            <td>{question.text}</td>
                            <td>
                                <button class="button is-primary" data-cy="show details" 
                                    onClick={() => onViewTags(question.questionId)}>
                                    Details
                            </button>
                            </td>
                        </tr>
                    ))
                }
            </tbody>
        </table>


    </div>
);

export default DumbQuestionsList;
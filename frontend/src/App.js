import React from 'react';
import './App.css';


import { HashRouter, Switch, Route } from "react-router-dom"
import SmartQuestionDetails from './view/SmartQuestionDetails';
import SmartQuestionsList from './view/SmartQuestionsList';
import SmartCreateQuestion from './view/SmartCreateQuestion';
import SmartFilterQuestionsByTag from './view/SmartFilterQuestionsByTag';
import SmartFilterQuestionsByTitle from './view/SmartFilterQuestionsByTitle';
import SmartCreateAnswer from './view/SmartCreateAnswer';
import SmartAnswersByScore from './view/SmartAnswersByScore';

const App = () => (
  //curly brackets used to pass a variable from js
  <div className="App">
    <HashRouter>
      <Switch>
        <Route exact={true} component={SmartQuestionsList} path="/" />
        <Route exact={true} component={SmartCreateQuestion} path="/create-question" />
        <Route exact={true} component={SmartQuestionDetails} path="/question-details/:index" />
        <Route exact={true} component={SmartFilterQuestionsByTag} path="/filter-by-tag/:searchWord" />
        <Route exact={true} component={SmartFilterQuestionsByTitle} path="/filter-by-title/:searchWord" />
        <Route exact={true} component={SmartCreateAnswer} path="/create-answer" />
        <Route exact={true} component={SmartAnswersByScore} path="#/answers-by-score"/>
      </Switch>
    </HashRouter>
  </div>
);


export default App;

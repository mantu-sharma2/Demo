import React from 'react';
import './App.css';
import Card from './components/Card';
import data from './data/cardData.json';

function App() {
  // console.log(data);

  return (
    <div className="App">
      <h1 id="title">Welcome!</h1>
      <div className="CardContainer">
        {data.map((cardData) => (
          <Card data={cardData} key={cardData.id} />
        ))}
      </div>
    </div>
  );
}

export default App;
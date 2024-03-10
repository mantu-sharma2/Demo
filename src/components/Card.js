import React from 'react';
import '../css/card.css'

const Card = ({ data }) => {
  return (
    <a href={data.link} className="card">
      <div className="card-image">
        <img src={data.imageLink} alt="Card Image" />
      </div>
      <div className="card-content">
        <h2>{data.topic}</h2>
        <p>{data.description}</p>
      </div>
    </a>
  );
};

export default Card;
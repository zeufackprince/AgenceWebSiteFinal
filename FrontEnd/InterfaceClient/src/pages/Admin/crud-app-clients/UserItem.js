import React from 'react';
import { BsBanFill } from "react-icons/bs";
import { FaUserMinus } from "react-icons/fa";

const UserItem = ({ User, onDelete }) => {
  return (
    <tr>
      <td>{User.nom}</td>
      <td>{User.email}</td>
        <td>{User.numero}</td>
      <td className='btn-action'>
        <i onClick={() => alert(`Block ${User.nom}`)}>
            <BsBanFill />
        </i>
        <i onClick={() => alert(`Delete ${User.nom}`)}>
            <FaUserMinus />
        </i>
      </td>
    </tr>
  );
};

export default UserItem;

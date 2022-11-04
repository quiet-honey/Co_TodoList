import React, { useState } from "react";
import {
  ListItem,
  ListItemText,
  InputBase,
  Checkbox,
  ListItemSecondaryAction,
  IconButton,
} from "@material-ui/core";
import { DeleteOutlined } from "@material-ui/icons";

const Todo = (props) => {
  const [state, setState] = useState({ item: props.item, readOnly: true });
  const deleteItem = props.deleteItem;
  const update = props.update;

  console.log(state.item.id);

  const deleteEventHandler = () => {
    deleteItem(state.item);
  };

  const enterKeyEventHandler = (e) => {
    if (e.key === "Enter") {
      setState({ item: state.item, readOnly: true });
      update(state.item);
    }
  };

  const editEventHandler = (e) => {
    const thisItem = state.item;
    thisItem.title = e.target.value;
    setState({ item: thisItem, readOnly: state.readOnly });
  };

  const checkBoxEventHandler = () => {
    const thisItem = state.item;
    thisItem.done = !thisItem.done;
    setState({ item: thisItem, readOnly: state.readOnly });
    update(state.item);
  };

  const offReadOnlyMode = () => {
    setState({ item: state.item, readOnly: false });
  };

  const item = state.item;

  return (
    <ListItem>
      <Checkbox checked={item.done} onChange={checkBoxEventHandler} />
      <ListItemText>
        <InputBase
          inputProps={{
            "aria-label": "naked",
            readOnly: state.readOnly,
          }}
          type="text"
          id={item.id}
          name={item.id}
          value={item.title}
          multiline={true}
          fullWidth={true}
          onClick={offReadOnlyMode}
          onChange={editEventHandler}
          onKeyDown={enterKeyEventHandler}
        />
      </ListItemText>

      <ListItemSecondaryAction>
        <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
          <DeleteOutlined />
        </IconButton>
      </ListItemSecondaryAction>
    </ListItem>
  );
};

export default Todo;

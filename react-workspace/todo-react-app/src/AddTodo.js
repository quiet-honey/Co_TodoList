import React, { useState } from "react";
import { TextField, Paper, Button, Grid } from "@material-ui/core";

const AddTodo = (props) => {
  let [state, setState] = useState({ item: { title: "" } });
  const add = props.add;

  const onInputChange = (e) => {
    const thisItem = state.item;
    thisItem.title = e.target.value;
    setState({ item: thisItem });
  };

  const onButtonClick = () => {
    add(state.item);
    setState({ item: { title: "" } }); // 버튼을 클릭하면 입력 텍스트 창 비우기
  };

  const enterKeyEventHandler = (e) => {
    if (e.key === "Enter") {
      onButtonClick();
    }
  };

  return (
    <Paper style={{ margin: 16, padding: 16 }}>
      <Grid container>
        <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
          <TextField
            placeholder="Add Todo here"
            fullWidth
            onChange={onInputChange}
            value={state.item.title}
            onKeyDown={enterKeyEventHandler}
          />
        </Grid>
        <Grid xs={1} md={1} item>
          <Button
            fullWidth
            color="secondary"
            variant="outlined"
            onClick={onButtonClick}
          >
            +
          </Button>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default AddTodo;

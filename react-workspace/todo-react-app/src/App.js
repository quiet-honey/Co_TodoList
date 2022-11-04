import React, { useState, useEffect } from "react";
import Todo from "./Todo";
import AddTodo from "./AddTodo";
import { Paper, List, Container } from "@material-ui/core";
import "./App.css";
import { call } from "./service/ApiService";

const App = () => {
  const [state, setState] = useState({
    items: [],
  });

  console.log("App rerendering");

  useEffect(() => {
    console.log("App rendering by useEffect");
    call("/todo/find", "GET", null).then((response) => {
      setState({ items: response.data });
    });
  }, []);

  const add = (item) => {
    call("/todo/create", "POST", item).then((response) => {
      setState({ items: response.data });
    });
  };

  const deleteItem = (item) => {
    call("/todo/delete", "DELETE", item).then((response) => {
      setState({ items: response.data });
    });
  };

  const update = (item) => {
    call("/todo/update", "PUT", item).then((response) => {
      setState({ items: response.data });
    });
  };

  let todoItems = state.items.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {state.items.map((item, idx) => (
          <Todo
            item={item}
            key={item.id}
            deleteItem={deleteItem}
            update={update}
          />
        ))}
      </List>
    </Paper>
  );

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo add={add} />
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
};

export default App;

import axios from "axios";
import { GET_ERRORS, GET_PROJECT, GET_PROJECTS, DELETE_PROJECT } from "./types";
export const createProject = (project, history) => async (dispatch) => {
  try {
    await axios.post("http://localhost:8080/api/project/add-project", project);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const getProjects = () => async (dispatch) => {
  const res = await axios.get("http://localhost:8080/api/project/all-project");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data,
  });
};
export const getProject = (id, history) => async (dispatch) => {
  try {
    const res = await axios.get(
      `http://localhost:8080/api/project/find-project/${id}`
    );
    dispatch({
      type: GET_PROJECT,
      payload: res.data,
    });
  } catch (error) {
    history.push("/dashboard");
  }
};
export const deleteProject = (id, history) => async (dispatch) => {
  if (window.confirm("Are you sure?")) {
    await axios.delete(
      `http://localhost:8080/api/project/delete-project/${id}`
    );
    dispatch({
      type: DELETE_PROJECT,
      payload: id,
    });
  }
};

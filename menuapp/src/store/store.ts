import { configureStore } from "@reduxjs/toolkit";
import menuSlice from "../features/menuSlice";

let store = configureStore({
    reducer : {
        menus : menuSlice
    }
});

export type RootState = ReturnType<typeof store.getState>;
export default store;
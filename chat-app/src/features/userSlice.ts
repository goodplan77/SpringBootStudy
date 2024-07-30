import { createSlice , PayloadAction} from "@reduxjs/toolkit";
import { initMember, Member } from "../type/type";

const userSlice = createSlice({
    name : 'user',
    initialState : initMember ,
    reducers : {
        setUser : (state , action:PayloadAction<Member>) => {
            return action.payload;
        },
        logout : (state) => {
            return initMember;
        }
    }
})

export const {setUser , logout} = userSlice.actions;
export default userSlice.reducer;
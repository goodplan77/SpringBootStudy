export type MenuType = 'kr'|'jp'|'ch';
export type MenuTaste = 'mild'|'hot';

export interface Menu{
    id : number,
    restaurant : string,
    name : string,
    price : number,
    type : MenuType,
    taste : MenuTaste
}

export const initialMenu:Menu = {
    id : 0,
    restaurant : '',
    name : '',
    price : 0,
    type : 'kr',
    taste : 'mild'
} as const; 
// 값이 바뀔수 있으므로 (공통으로 사용?) const 지정
// (리터럴 + readonly)

export const initialMenuList:Menu[] = [];
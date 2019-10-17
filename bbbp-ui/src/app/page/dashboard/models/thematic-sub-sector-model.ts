import { ThematicIndicatorModel } from "./thematic-indicator-model";

export class ThematicSubSectorModel {
        subSectorId: number;
        subSectorName: string;
        indicators: ThematicIndicatorModel[];
        controlType?:string;
        type?:string;
    
}

import { Component } from '@angular/core';
import {MazeGenerationRcServiceService} from "../../services/maze-generation-rc-service.service";

@Component({
  selector: 'app-results-component',
  templateUrl: './results-component.component.html',
  styleUrls: ['./results-component.component.css']
})
export class ResultsComponentComponent {
constructor(public mazeGenerationRcServiceService:MazeGenerationRcServiceService) {
}
}

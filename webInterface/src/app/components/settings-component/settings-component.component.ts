import {Component} from '@angular/core';
import {MazeGenerationRcServiceService} from "../../services/maze-generation-rc-service.service";

@Component({
  selector: 'app-settings-component',
  templateUrl: './settings-component.component.html',
  styleUrls: ['./settings-component.component.css']
})
export class SettingsComponentComponent {
  public file?: File;

  constructor(public mazeGenerationRcServiceService: MazeGenerationRcServiceService) {
  }

  public generateNewMaze(): void {
    if (this.file){
      this.mazeGenerationRcServiceService.generateMaze(this.file);
    }
  }

  public onFileSelected(event: any): void {
    this.file = event.target.files[0];
    if (this.file){
      this.mazeGenerationRcServiceService.generateMaze(this.file);
    }
  }
}
